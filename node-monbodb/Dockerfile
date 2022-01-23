FROM node:16-alpine AS builder

WORKDIR /app

ADD package*.json ./
ADD .npmrc ./
ADD . .

RUN npm i && npm run build

FROM node:16-alpine as final

WORKDIR /app

COPY --chown=node:node --from=builder /app/build /app/
COPY --chown=node:node --from=builder /app/*.json /app/
COPY --chown=node:node --from=builder /app/tsconfig-paths.bootstrap.js /app/

RUN npm install --only=prod

USER node

EXPOSE 3000

CMD [ "npm", "start" ]