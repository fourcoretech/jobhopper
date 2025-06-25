# AI-Powered Resume Analyzer Frontend

A modern React application built with Vite and Tailwind CSS for the AI-powered resume analyzer.

## Phase 1 - ✅ Completed

**Project Foundation & Setup**
- ✅ React project with Vite
- ✅ Tailwind CSS with custom design system
- ✅ Project structure with organized folders
- ✅ Core dependencies (React Router, Axios, React Hook Form, Zod, Framer Motion, Lucide React)
- ✅ Environment variables for API endpoints
- ✅ Error boundary for application-level error handling
- ✅ Basic routing structure with placeholder pages

## Quick Start

```bash
# Install dependencies
npm install

# Start development server (requires Node.js 20+)
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

## Project Structure

```
src/
├── components/     # Reusable UI components
├── pages/         # Page components
├── hooks/         # Custom React hooks
├── utils/         # Utility functions
├── context/       # React context providers
├── services/      # API integration
└── App.jsx        # Main application component
```

## API Integration

The application is configured to connect to the following backend services:

- **Auth Service**: http://localhost:8080
- **Resume Analyzer**: http://localhost:8082
- **Interview Prep**: http://localhost:8081
- **Notifications**: http://localhost:8083

## Current Features

- ✅ Professional homepage with three main tool cards
- ✅ Basic routing (/, /login, /register, /resume-analyzer, /job-matcher, /interview-prep)
- ✅ Error boundary with user-friendly error handling
- ✅ Responsive design foundation
- ✅ API service layer with authentication handling

## Next Steps

**Phase 2 - Core UI Components** (Coming Next)
- Header with navigation and dark/light mode toggle
- Reusable Button, Card, and Input components
- FileUpload component with drag-and-drop
- Modal and Toast notification system
- Loading components

## Tech Stack

- **React 18** with functional components and hooks
- **Vite** for fast development and optimized builds
- **Tailwind CSS** for utility-first styling
- **React Router v6** for client-side routing
- **Axios** for HTTP requests
- **React Hook Form** with Zod validation (ready for future phases)
- **Framer Motion** for animations (ready for future phases)
- **Lucide React** for icons