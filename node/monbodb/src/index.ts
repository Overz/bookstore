import { envs, ENV } from '@utils';
import { server } from 'app';
import { Connection } from 'mongoose';
import { connect } from '@models';
import { config } from 'dotenv';
import { resolve } from 'path';

let db: Connection;

const start = async () => {
  try {
    console.log('[ENVIRONMENTS] Reading envs...');
    config({ path: resolve(__dirname, '..', '.env') });
    envs();

    console.log('[ENVIRONMENTS] Printing...', ENV);
  } catch (err) {
    exit('[ENVIRONMENTS] Error reading envs', err);
  }

  try {
    console.log('[DATABASE] Connecting to database..');
    db = await connect(ENV['DB_URL']);
  } catch (err) {
    exit('[DATABASE] Error trying to connect!', err);
  }

  try {
    const app = server();
    app.listen(ENV['PORT'], () =>
      console.log(
        `[APP] '${ENV['APP_NAME']}' listening on port '${ENV['PORT']}'`
      )
    );
  } catch (err) {
    exit('[APP] Error initalizing the http server', err);
  }
};

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const exit = async (msg: string, error?: any) => {
  console.error(msg, '\n', error);
  process.exit(1);
};

const finish = async () => {
  console.log('[APP] Cleaning up...');
  if (db) {
    await db.close();
  }

  process.exit(0);
};

process.on('SIGINT', finish);
process.on('SIGTERM', finish);

start();
