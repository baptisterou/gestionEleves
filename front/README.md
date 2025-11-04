# Frontend - Gestion Élèves

Ce dossier contient une application front minimaliste, propre et prête à l'emploi, bâtie avec React + Vite et Tailwind CSS.

## Fonctionnalités incluses
- Authentification via le backend (`/auth/login`) avec stockage du token JWT dans `localStorage`.
- Protection de route pour le tableau de bord.
- Page de connexion simple et propre (email + mot de passe).
- Dashboard affichant les informations de l'utilisateur connecté depuis `/users/me`.
- Rôles et routage par rôle: `ADMIN` (/admin), `ENSEIGNANT` (/enseignant), `RESPONSABLE` (/responsable).
- Export de bulletins (PDF) côté Responsable, par trimestre T1/T2/T3 (GET blob sur l'API).
- Configuration Tailwind prête avec quelques utilitaires (boutons, inputs, cartes).

## Prérequis
- Node.js 18+ (recommandé)
- Un backend accessible (par défaut sur `http://localhost:8080`)

## Configuration
Copiez le fichier d'exemple d'environnement puis ajustez l'URL de l'API si besoin :

```bash
cd front
cp .env.example .env
# Sous Windows PowerShell :
# copy .env.example .env
```

Éditez `.env` si votre backend n'est pas sur `http://localhost:8080` :

```
VITE_API_BASE_URL=http://localhost:8080
```

## Démarrer en local
Installez les dépendances puis lancez le serveur de dev Vite :

```bash
cd front
npm install
npm run dev
```

Ouvrez l'application sur http://localhost:5173

## Structure du projet
```
front/
├─ index.html
├─ package.json
├─ postcss.config.js
├─ tailwind.config.js
├─ vite.config.js
├─ .env.example
└─ src/
   ├─ main.jsx
   ├─ index.css
   ├─ App.jsx
   ├─ lib/
   │  ├─ api.js
   │  └─ auth.js
   ├─ components/
   │  └─ Navbar.jsx
   └─ pages/
      ├─ Login.jsx
      └─ Dashboard.jsx
```

## Notes
- Le backend protège `/users/**` et la majorité des routes sous `/api/**` via JWT. Nous utilisons l'en-tête `Authorization: Bearer <token>` côté front.
- En cas de 401, le token est effacé et l'utilisateur est redirigé vers `/login` lors du prochain accès protégé.
- Vous pouvez étendre le Dashboard pour lister les élèves, matières, etc. en consommant les endpoints existants (`/api/eleve`, `/api/matiere`, `/api/classe`, ...).

## Build de production
```bash
npm run build
npm run preview
```

Le build de production est généré dans `dist/`.
