import { Schema } from 'mongoose';

export interface IBook {
  cdBook: string;
  nmBook: string;
  nmAuthor: string;
  deDescription: string;
  dtCreated: number;
}

export const PK_BOOK_SIZE = 10;

const bookSchema = new Schema<IBook>({
  cdBook: {
    alias: 'id',
    type: String,
    required: true,
    unique: true,
    auto: true,
  },
  nmBook: {
    alias: 'book',
    type: String,
    required: true,
  },
  nmAuthor: {
    alias: 'author',
    type: String,
    required: true,
  },
  deDescription: {
    alias: 'description',
    type: String,
    required: true,
  },
  dtCreated: {
    alias: 'created_at',
    type: Number,
    required: true,
    default: Date.now(),
  },
});

// bookSchema.pre('save', async function (next, opts) {
//
// });

export { bookSchema };
