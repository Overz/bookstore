/* eslint-disable @typescript-eslint/no-explicit-any */
import { Document, FlattenMaps, LeanDocument, Types } from 'mongoose';

export type Indexable = { [key: string]: any };

export type TypedInexable<T> = {
  [key in keyof T]?: any;
};

export type DocProps = { _id: string; __v: string };

export type FlatDoc<T> = FlattenMaps<
  LeanDocument<
    Document<any, any, T> &
      T & {
        _id: Types.ObjectId;
      }
  >
>;

export type Doc<T> = Document<any, any, T> &
  T & {
    _id: Types.ObjectId;
  };
