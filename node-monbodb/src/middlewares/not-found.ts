import { Request, Response, NextFunction } from 'express';

export const notFoundHandler = (
  req: Request,
  res: Response,
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  next: NextFunction
) => {
  console.log(`[APP] Request not found: ${req.method} - ${req.url}`);
  res.status(404).send('Not found');
};
