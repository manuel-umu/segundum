// Servidor Express que actua como host del frontend React en produccion:
//  - sirve los ficheros estaticos del build de React (express.static)
//  - reenvia las peticiones de datos a la pasarela de Segundum con fetch
//  - renderiza con Handlebars las paginas de error del lado del servidor
//    (por ejemplo un 502 si Segundum no responde)
//
// En desarrollo se usa el servidor de Create React App (npm start), que ya
// reenvia a Segundum con su proxy; este servidor sirve la aplicacion una vez
// construida con "npm run build" en la raiz del proyecto.

const path = require('path');
const express = require('express');
const { engine } = require('express-handlebars');

const app = express();
// Puerto configurable por variable de entorno; 3001 por defecto
// (3000 lo usa React en desarrollo y 8090 la pasarela de Segundum).
const PORT = process.env.PORT || 3001;
// URL de la pasarela de Segundum a la que reenviamos las peticiones de datos.
const API_URL = process.env.API_URL || 'http://localhost:8090';
// Carpeta con el build de React (se genera con "npm run build" en la raiz).
const BUILD_DIR = path.join(__dirname, '..', 'build');

// Helpers propios para las plantillas (mecanismo de express-handlebars).
// 'anyo' devuelve el anio actual para el pie de pagina.
const helpers = {
  anyo: function () {
    return new Date().getFullYear();
  },
};

// Devuelve un titulo y un mensaje en castellano para un codigo de error HTTP.
// Se usa tanto en la ruta /error/:codigo como en el manejador de errores.
function describirError(status) {
  if (status === 401) {
    return {
      titulo: 'No has iniciado sesion',
      mensaje: 'Necesitas identificarte para acceder a esta pagina.',
    };
  }
  if (status === 403) {
    return {
      titulo: 'Acceso denegado',
      mensaje: 'No tienes permiso para acceder a este recurso.',
    };
  }
  if (status === 404) {
    return {
      titulo: 'Pagina no encontrada',
      mensaje: 'El recurso que buscas no existe o ha sido eliminado.',
    };
  }
  if (status === 502) {
    return {
      titulo: 'Servidor de datos no disponible',
      mensaje: 'No se pudo contactar con el servidor de datos. Intentalo mas tarde.',
    };
  }
  return {
    titulo: 'Algo ha ido mal',
    mensaje: 'Se ha producido un error inesperado.',
  };
}

// Configuracion del motor de plantillas Handlebars. El layout por defecto es
// views/layout.hbs y los partials viven en views/partials.
app.engine('hbs', engine({
  extname: '.hbs',
  defaultLayout: 'layout',
  layoutsDir: path.join(__dirname, 'views'),
  partialsDir: path.join(__dirname, 'views', 'partials'),
  helpers: helpers,
}));
app.set('view engine', 'hbs');
app.set('views', path.join(__dirname, 'views'));

// Middleware propio que registra por consola cada peticion recibida.
app.use(function (req, res, next) {
  console.log(req.method + ' ' + req.url);
  next();
});

// Prefijos de la API de Segundum. Cualquier peticion a estas rutas se reenvia a
// la pasarela; el resto de rutas las atiende el frontend React.
const RUTAS_API = ['/auth', '/usuarios', '/productos', '/categorias', '/compraventas'];

// Lee el cuerpo crudo de la peticion (sin parsearlo) para poder reenviarlo
// intacto a Segundum. Solo se aplica a las rutas que se reenvian.
function leerCuerpoCrudo(req, res, next) {
  const trozos = [];
  req.on('data', function (trozo) {
    trozos.push(trozo);
  });
  req.on('end', function () {
    req.cuerpoCrudo = Buffer.concat(trozos);
    next();
  });
  req.on('error', next);
}

// Reenvia la peticion a Segundum con fetch (incluido en Node) y devuelve al
// cliente la respuesta de Segundum. Asi el navegador habla solo con nuestro
// servidor (mismo origen) y nosotros hablamos con Segundum por detras.
async function reenviarAApi(req, res, next) {
  try {
    // Reconstruimos la URL en Segundum conservando la ruta y los parametros.
    const urlDestino = API_URL + req.originalUrl;

    // Copiamos solo las cabeceras relevantes (entre ellas la cookie de sesion).
    const cabeceras = {};
    if (req.headers['content-type']) {
      cabeceras['content-type'] = req.headers['content-type'];
    }
    if (req.headers['cookie']) {
      cabeceras['cookie'] = req.headers['cookie'];
    }
    if (req.headers['authorization']) {
      cabeceras['authorization'] = req.headers['authorization'];
    }

    // Preparamos las opciones de la peticion a Segundum.
    const opciones = { method: req.method, headers: cabeceras };
    // GET y HEAD no llevan cuerpo; el resto reenvia el cuerpo recibido.
    if (req.method !== 'GET' && req.method !== 'HEAD') {
      opciones.body = req.cuerpoCrudo;
    }

    const respuesta = await fetch(urlDestino, opciones);

    // Reenviamos al cliente las cookies que Segundum quiera establecer.
    const cookies = respuesta.headers.getSetCookie();
    if (cookies.length > 0) {
      res.setHeader('set-cookie', cookies);
    }

    // Reenviamos el tipo de contenido y el codigo de estado de Segundum.
    const tipo = respuesta.headers.get('content-type');
    if (tipo) {
      res.setHeader('content-type', tipo);
    }
    res.status(respuesta.status);

    // Enviamos el cuerpo tal cual lo devuelve Segundum.
    const cuerpo = Buffer.from(await respuesta.arrayBuffer());
    res.send(cuerpo);
  } catch (error) {
    // Si Segundum no responde, generamos un error 502 (puerta de enlace).
    const err = new Error('No se pudo contactar con el servidor de datos');
    err.status = 502;
    next(err);
  }
}

app.use(RUTAS_API, leerCuerpoCrudo, reenviarAApi);

// Pagina de error que el frontend solicita (con una redireccion de pagina
// completa) cuando una peticion al backend devuelve un error. El navegador
// navega aqui de verdad, asi que Express puede renderizar el HTML que ve el
// usuario. Ej.: un 403 de Segundum hace que React redirija a /error/403.
app.get('/error/:codigo', function (req, res) {
  let status = Number(req.params.codigo);
  // Si el codigo no es un numero de error valido, usamos 500 por defecto.
  if (isNaN(status) || status < 400 || status > 599) {
    status = 500;
  }
  const info = describirError(status);
  res.status(status);
  res.render('error', {
    title: 'Error ' + status,
    status: status,
    titulo: info.titulo,
    mensaje: info.mensaje,
  });
});

// Ficheros estaticos propios (CSS de las paginas de error).
app.use(express.static(path.join(__dirname, 'public')));
// Ficheros estaticos del build de React.
app.use(express.static(BUILD_DIR));

// Cualquier otra peticion GET devuelve el index.html del build para que sea
// React Router quien muestre la vista correspondiente en el cliente.
app.get('*', function (req, res, next) {
  res.sendFile(path.join(BUILD_DIR, 'index.html'), function (err) {
    // Si no existe el build (no se ha hecho "npm run build"), pasa al error.
    if (err) next(err);
  });
});

// Si la peticion llega hasta aqui (metodo no GET sin ruta), es un 404.
app.use(function (req, res, next) {
  const err = new Error('Pagina no encontrada');
  err.status = 404;
  next(err);
});

// Manejador de errores generico: cualquier error que llegue aqui se renderiza
// con la plantilla error.hbs y el codigo de estado adecuado. Lleva cuatro
// parametros (err, req, res, next), que es como Express distingue un
// middleware de error de uno normal.
app.use(function (err, req, res, next) {
  // Si el error no trae estado, asumimos un 500 (error interno del servidor).
  const status = err.status || 500;
  const info = describirError(status);
  res.status(status);
  res.render('error', {
    title: 'Error ' + status,
    status: status,
    titulo: info.titulo,
    mensaje: info.mensaje,
  });
});

app.listen(PORT, function () {
  console.log('Servidor escuchando en http://localhost:' + PORT);
});
