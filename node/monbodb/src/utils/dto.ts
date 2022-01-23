import { DocProps, Indexable, TypedInexable } from './types';

type Dto<T> = {
  schema: Indexable;
  data: T;
  skip?: string[];
  replace?: TypedInexable<T & DocProps>;
};

export const dto = <T>({
  data,
  schema,
  skip = [],
  replace = {},
}: Dto<T>): Indexable => {
  const keys = Object.keys(data);

  const cache: Indexable = {};
  for (const key of keys) {
    if (skip.includes(key)) {
      continue;
    }

    const obj = schema[key];
    if (!obj) {
      continue;
    }

    let alias = obj.alias;
    if (!alias || ['_id', '__v'].includes(key)) {
      continue;
    }

    if (JSON.stringify(replace) !== '{}') {
      alias = replace[key as keyof T];
    }

    cache[alias] = data[key as keyof T];
  }

  return cache;
};
