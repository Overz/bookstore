import { Book } from '@models';
import { Router, Request, Response } from 'express';

const router = Router();

router.delete('/api/book/:cdBook', async (req: Request, res: Response) => {
  await Book.deleteOne({ cdBook: req.params.cdBook });
  res.status(204).send();
});

export { router as deleteBookRouter };
