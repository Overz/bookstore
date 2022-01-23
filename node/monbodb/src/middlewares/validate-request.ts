import { Request, Response, NextFunction } from 'express';
import { validationResult } from 'express-validator';

export const validateRequest = (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  const result = validationResult(req);

  if (!result.isEmpty()) {
    const arr = result.array();
    const error = {
      message: arr[0].msg,
      errors: arr.map(({ msg }) => msg),
    };

    return res.status(400).send(error);
  }

  next();
};
