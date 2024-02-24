FROM node:lts-alpine

WORKDIR /frontend
COPY . .

RUN npm cache clean --force
RUN npm install --force

CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]