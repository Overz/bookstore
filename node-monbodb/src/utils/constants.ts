import { get as env, VariableAccessors } from 'env-var';

export const NODE_ENV = process.env.NODE_ENV;
export const required = NODE_ENV !== 'test' && !process.env.TS_NODE_DEV;

type EnvKeys = 'DB_URL' | 'APP_NAME' | 'PORT';

type EnvsProps = {
  [K in EnvKeys]: {
    required: boolean;
    type: keyof VariableAccessors;
  };
};

// eslint-disable-next-line @typescript-eslint/no-unused-vars
let setup: EnvsProps | null = {
  APP_NAME: {
    required,
    type: 'asString',
  },
  DB_URL: {
    required,
    type: 'asString',
  },
  PORT: {
    required,
    type: 'asPortNumber',
  },
};

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export type Envs = { [K in EnvKeys]: any };
export const ENV = {} as Envs;

export const envs = () => {
  if (!setup) {
    throw new Error('[Constants] Setup is undefined');
  }

  const keys = Object.keys(setup);

  for (const key of keys) {
    const { required, type } = setup[key as keyof EnvsProps];
    ENV[key as keyof Envs] = (env(key).required(required)[type] as Function)();
  }

  setup = null;
};
