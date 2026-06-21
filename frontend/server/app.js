const path = require('path');
const express = require('express');
const { engine } = require('express-handlebars');

const app = express();
const PORT = 3001;
const API_URL = 'http://localhost:8090';
const BUILD_DIR = path.join(__dirname, '..', 'build');

function describirError(status) {
  if (status === 401) {
    return {
      titulo: 'Credenciales inválidas',
      mensaje: 'Tus credenciales no son válidas.',
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
  if (status === 500) {
    return {
      titulo: 'Error interno del servidor',
      mensaje: 'Se ha producido un error interno en el servidor.',
    };
  }
  if (status === 502) {
    return {
      titulo: 'Error de comunicacion',
      mensaje: 'No se pudo contactar con el servidor de datos. Intentalo mas tarde.',
    };
  }
  return {
    titulo: 'Algo ha ido mal',
    mensaje: 'Se ha producido un error inesperado.',
  };
}

app.engine('hbs', engine({
  extname: '.hbs',
  defaultLayout: 'layout',
  layoutsDir: path.join(__dirname, 'views'),
  helpers: helpers,
}));
app.set('view engine', 'hbs');
app.set('views', path.join(__dirname, 'views'));

app.use(function (req, res, next) {
  console.log(req.method + ' ' + req.url);
  next();
});

const RUTAS_API = ['/auth', '/usuarios', '/productos', '/categorias', '/compraventas'];

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

// Rutas validas del frontend React: solo para estas servimos el index.html
// (React Router se encarga de mostrar la vista en el cliente). Debe
// mantenerse en sintonia con las rutas declaradas en src/App.js. Cualquier
// otra URL se considera no encontrada y la atiende el manejador 404 de abajo,
// que muestra la pagina de error de Handlebars.
const RUTAS_REACT = [
  '/',
  '/login',
  '/registro',
  '/misproductos',
  '/miscompras',
  '/perfil',
  '/admin/usuarios',
  '/admin/compraventas',
];
app.get(RUTAS_REACT, function (req, res, next) {
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
