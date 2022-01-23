import { validateRequest } from '@middlewares';
import { Book } from '@models';
import { Router, Request, Response } from 'express';
import { body } from 'express-validator';

const router = Router();

router.put(
  '/api/book/:cdBook',
  [
    body('book').optional().notEmpty().withMessage('he book name is required'),
    body('author').optional().notEmpty().withMessage('The author is required'),
    body('description')
      .optional()
      .notEmpty()
      .withMessage('The description is required'),
  ],
  validateRequest,
  async (req: Request, res: Response) => {
    const { book, author, description } = req.body;
    const { cdBook } = req.params;

    const count = await Book.count({ cdBook });
    if (!count) {
      return res.status(404).send('Not found');
    }

    await Book.updateOne(
      { cdBook },
      {
        $set: {
          deDescription: description,
          nmAuthor: author,
          nmBook: book,
        },
      }
    );

    res.status(204).send();
  }
);

export { router as updateBookRouter };
