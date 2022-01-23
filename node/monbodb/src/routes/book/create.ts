import { validateRequest } from '@middlewares';
import { Book, bookSchema, PK_BOOK_SIZE } from '@models';
import { dto } from '@utils';
import { Router, Request, Response } from 'express';
import { body } from 'express-validator';
import { nanoid } from 'nanoid';

const router = Router();

router.post(
  '/api/book',
  [
    body('book').notEmpty().withMessage('The book name is required'),
    body('author').notEmpty().withMessage('The author is required'),
    body('description')
      .trim()
      .notEmpty()
      .withMessage('The description is required'),
  ],
  validateRequest,
  async (req: Request, res: Response) => {
    const { book, author, description } = req.body;
    const b = new Book({
      cdBook: nanoid(PK_BOOK_SIZE),
      deDescription: description,
      nmAuthor: author,
      nmBook: book,
    });

    res.status(201).send(
      dto({
        data: (await b.save()).toJSON(),
        schema: bookSchema.obj,
      })
    );
  }
);

export { router as createBookRouter };
