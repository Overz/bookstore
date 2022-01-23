import { errorHandler, notFoundHandler } from '@middlewares';
import express, { Express } from 'express';
import { json, urlencoded } from 'body-parser';
import { routes } from 'routes';

let app: Express;

const server = () => {
  app = express();

  app.set('trust proxy', true);
  app.use(json());
  app.use(urlencoded({ extended: true }));

  app.use(routes);

  app.all('*', notFoundHandler);

  app.use(errorHandler);

  return app;
};

export { app, server };
