# e-nagorik

Citizen e-governance platform — apply for civic services, track applications, issue certificates. Built as a Java backend (raw `javac`, no framework) + React/Vite frontend.

## Tech
- **Backend**: Java, custom `WebServer` on port `8080`, file-based repositories. Showcases GoF design patterns (Command, Factory, Adapter, Facade, Singleton).
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

## Team & work split

| Branch | Member area | Owns |
|--------|-------------|------|
| `feature/m1-models`        | Backend — Domain Models   | `model/`, `util/`, `exception/` |
| `feature/m2-data-services` | Backend — Data + Services | `repository/`, `service/`, `singleton/`, `adapter/` |
| `feature/m3-patterns-web`  | Backend — Patterns + Web  | `command/`, `factory/`, `facade/`, `web/`, `Main.java` |
| `feature/m4-frontend-core` | Frontend — Scaffold/Shared| app shell, routing, `components/`, `context/`, `api/`, config |
| `feature/m5-citizen`       | Frontend — Citizen        | `pages/citizen/` |
| `feature/m6-officer-mayor` | Frontend — Officer + Mayor| `pages/officer/`, `pages/mayor/` |

Each member develops on their branch and merges to `main` via Pull Request.
