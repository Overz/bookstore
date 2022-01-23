import { Book, bookSchema } from '@models';
import { dto } from '@utils';
import { Router, Request, Response } from 'express';

const router = Router();

router.get('/api/book/:cdBook', async (req: Request, res: Response) => {
  const data = await Book.findOne({ cdBook: req.params });

  if (!data) {
    return res.status(404).send('Not Found');
  }

  res.send(dto({ data, schema: bookSchema.obj }));
});

export { router as getOneBookRouter };
