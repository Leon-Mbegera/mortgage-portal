# mortgage-portal — Frontend (React + TypeScript)

## Project overview
This is the **frontend** of the mortgage-portal full-stack application.  
It is a React 18 + TypeScript SPA using Redux Toolkit for state management and React Router for navigation.

## Companion project
The backend lives at `../backend/` (Spring Boot 3 + PostgreSQL + Kafka).  
See `../backend/CLAUDE.md` for API contracts, authentication flow, and domain model.

## Tech stack
| Concern | Technology |
|---|---|
| Language | TypeScript 5 |
| Framework | React 18 |
| State management | Redux Toolkit + RTK Query (or plain Axios in service layer) |
| Routing | React Router v6 |
| HTTP client | Axios |
| Forms | React Hook Form + Zod (validation) |
| Styling | Tailwind CSS (or CSS Modules) |
| Testing | Jest + React Testing Library |
| Linting | ESLint + Prettier |
| Build | Vite |
| Containerisation | Docker (nginx-served static build) |

## Folder structure
```
src/
├── api/             # Axios instance + base configuration
├── services/        # All API call functions (never call API from components)
├── store/           # Redux Toolkit slices + store config
├── hooks/           # Custom hooks (useAuth, useApplications, …)
├── components/      # Pure, reusable UI components with typed props
├── pages/           # Route-level components; compose components + connect to store
├── types/           # TypeScript interfaces for API shapes and domain objects
├── utils/           # Pure helper functions
└── App.tsx          # Router setup
```

## Key engineering rules
- **All API calls live in `src/services/`** — never in components or pages directly.
- **Components are pure**: receive typed props, emit typed callbacks — no Redux inside reusable components.
- **Pages connect to Redux** via `useSelector` / `useDispatch`; pages compose components.
- **TypeScript interfaces** for every API request/response shape in `src/types/`.
- **Custom hooks** in `src/hooks/` for any stateful logic shared across more than one component.
- **No `any` type** without an explanatory comment.
- **ESLint + Prettier** must pass before every commit.

## Pages & routes
| Route | Component | Role |
|---|---|---|
| `/login` | `LoginPage` | Public |
| `/register` | `RegisterPage` | Public |
| `/dashboard` | `DashboardPage` | USER |
| `/apply` | `ApplicationFormPage` | USER |
| `/admin` | `AdminPanelPage` | ADMIN |

## Running locally
```bash
npm install
npm run dev        # Vite dev server at http://localhost:5173
```

## Running tests
```bash
npm test
npm run test:coverage
```

## Linting
```bash
npm run lint
npm run format
```

## Environment variables
`.env.local` (gitignored):
```
VITE_API_BASE_URL=http://localhost:8080/api
```

## Backend API base URL
- Local: `http://localhost:8080/api`
- Swagger docs: `http://localhost:8080/swagger-ui.html`
