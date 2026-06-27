# e-nagorik

Citizen e-governance platform — apply for civic services, track applications, issue certificates.

## Tech
- **Backend**: Java, custom `WebServer` on port `8080`, file-based repositories.
- **Frontend**: React + Vite, role-based dashboards for **Citizen**, **Officer**, **Mayor**.

## Run

### Backend
```bash
cd e-nagorik-backend
find . -name '*.java' > sources.txt
mkdir -p out
javac -d out @sources.txt
java -cp out com.enagorik.Main      # -> "WebServer listening on port 8080"
```

### Frontend
```bash
cd e-nagorik-frontend
cp .env.example .env
npm install
npm run dev
```
