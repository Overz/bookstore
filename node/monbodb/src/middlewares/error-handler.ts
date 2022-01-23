import { Request, Response, NextFunction } from 'express';

export const errorHandler = (
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  error: any,
  req: Request,
  res: Response,
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  next: NextFunction
) => {
  console.error(error);

  res.status(500).send({ message: 'Internal server error' });
};
