import {
  createBookRouter,
  deleteBookRouter,
  getOneBookRouter,
  listAllBookRouter,
  updateBookRouter,
} from '@routes';
import { Router } from 'express';

const book: Router[] = [
  createBookRouter,
  updateBookRouter,
  getOneBookRouter,
  listAllBookRouter,
  deleteBookRouter,
];

export const routes: Router[] = [...book];
