# Utilizza l'ultima immagine di Node come base
FROM node:latest

# Imposta la directory di lavoro nel container
WORKDIR .

# Copia il file package.json e package-lock.json (se esiste) nella directory di lavoro del container
COPY package*.json ./

# Installa le dipendenze del progetto
RUN npm install --force

# Copia il resto dei file del progetto nella directory di lavoro del container
COPY . .

# Costruisci l'applicazione per la produzione usando Vite
RUN npm run build

# Installa http-server per servire l'applicazione costruita
RUN npm install -g http-server

# Espone la porta 8080 sul container
EXPOSE 8080

# Serve l'applicazione dalla directory 'dist' utilizzando http-server
CMD [ "http-server", "dist", "-p", "8080" ]
