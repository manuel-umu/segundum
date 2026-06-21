const path = require('path');
const fs = require('fs');
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

function mostrarError(res, status) {
  const info = describirError(status);
  res.status(status);
  res.render('error', {
    title: 'Error ' + status,
    status: status,
    titulo: info.titulo,
    mensaje: info.mensaje,
  });
}

app.engine('hbs', engine({
  extname: '.hbs',
  defaultLayout: 'layout',
  layoutsDir: path.join(__dirname, 'views'),
}));
app.set('view engine', 'hbs');
app.set('views', path.join(__dirname, 'views'));

// Parseo del cuerpo de la peticion
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

const RUTAS_API = ['/auth', '/usuarios', '/productos', '/categorias', '/compraventas'];

async function reenviarAApi(req, res) {
  try {
    const urlDestino = API_URL + req.originalUrl;

    const cabeceras = {};
    if (req.headers['cookie']) {
      cabeceras['cookie'] = req.headers['cookie'];
    }
    if (req.headers['authorization']) {
      cabeceras['authorization'] = req.headers['authorization'];
    }

    const opciones = { method: req.method, headers: cabeceras };
    // GET y HEAD no llevan cuerpo, el resto reenvia el cuerpo recibido como JSON 
    if (req.method !== 'GET' && req.method !== 'HEAD'
        && req.body && Object.keys(req.body).length > 0) {
      opciones.body = JSON.stringify(req.body);
      cabeceras['content-type'] = 'application/json';
    }

    const respuesta = await fetch(urlDestino, opciones);

    const cookies = respuesta.headers.getSetCookie();
    if (cookies.length > 0) {
      res.setHeader('set-cookie', cookies);
    }

    const tipo = respuesta.headers.get('content-type');
    if (tipo) {
      res.setHeader('content-type', tipo);
    }
    res.status(respuesta.status);

    const cuerpo = await respuesta.text();
    res.send(cuerpo);
  } catch (error) {
    mostrarError(res, 502);
  }
}

app.use(RUTAS_API, reenviarAApi);

// Paginas de error
app.get('/error/:codigo', function (req, res) {
  let status = Number(req.params.codigo);
  if (isNaN(status) || status < 400 || status > 599) {
    status = 500;
  }
  mostrarError(res, status);
});

app.use(express.static(path.join(__dirname, 'public')));
app.use(express.static(BUILD_DIR));

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

// Todas las rutas de React se sirven con el mismo index
app.get(RUTAS_REACT, function (req, res) {
  const rutaIndex = path.join(BUILD_DIR, 'index.html');
  fs.readFile(rutaIndex, function (err, datos) {
    if (err) {
      mostrarError(res, 500);
    } else {
      res.send(datos.toString());
    }
  });
});

// Si la peticion llega hasta aqui, no coincide con ninguna ruta: es un 404
app.use(function (req, res) {
  mostrarError(res, 404);
});
