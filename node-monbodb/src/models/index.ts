import { Indexable } from '@utils';
import mongoose, { ConnectOptions, Connection, Model } from 'mongoose';
import { IBook, bookSchema } from './book';

export * from './book';

export let Book: Model<IBook>;

mongoose.Promise = global.Promise;

export const connect = async (
  url: string,
  opts?: ConnectOptions
): Promise<Connection> => {
  const connStatus: Indexable = {
    0: 'disconnected',
    1: 'connected',
    2: 'connecting',
    3: 'disconnecting',
  };

  const db = await mongoose.createConnection(url, opts).asPromise();
  console.log(`[DATABASE] Status'${connStatus[db.readyState]}'`);

  Book = db.model<IBook>('book', bookSchema);

  return db;
};
