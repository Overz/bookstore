import { Router, Request, Response } from 'express';
import { Book, bookSchema, IBook } from '@models';
import { dto, Doc, Indexable } from '@utils';

const router = Router();

router.get('/api/book', async (req: Request, res: Response) => {
  const { id, book, author, description } = req.query;

  const sort: Indexable = {};
  if (req.query.sort) {
    const [key, value] = ((req.query.sort as string) || '').split(',');
    sort[key] = value;
  }

  const page = Number(req.query.page || 0);
  const pageSize = Number(req.query.pageSize || 20);
  const totalRecords = await Book.count();

  const find: Indexable = {};

  if (id) {
    find.cdBook = id as string;
  }

  if (book) {
    find.nmBook = book as string;
  }

  if (author) {
    find.nmAuthor = author as string;
  }

  if (description) {
    find.deDescription = description as string;
  }

  const result: Doc<IBook>[] = await Book.find(find)
    .limit(pageSize)
    .skip(pageSize * page)
    .sort(sort);

  const data = result.map((data: Doc<IBook>) =>
    dto({ data: data.toJSON(), schema: bookSchema.obj })
  );

  res.send({ page, pageSize, totalRecords, data });
});

export { router as listAllBookRouter };
