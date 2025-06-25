# AI‑Powered Resume Analyzer — **Frontend Delivery Plan (MVP‑Compatible)**

> **Goal:** Build a polished, mobile‑first React UI that *only* relies on the **existing backend endpoints**:
>
> | Service         | Port | Required End‑points                                  |
> | --------------- | ---- | ---------------------------------------------------- |
> | Auth            | 8080 | **POST /auth/register**, **POST /auth/login**        |
> | Resume Analyzer | 8082 | **POST /resume/analyze**, **POST /resume/job‑match** |
> | Interview Prep  | 8081 | **POST /resume/interview‑prep**                      |


---

## Phase 1 – Project Foundation (Checkpoint 1)

|  Task                                                                            | Notes                   |
| -------------------------------------------------------------------------------- | ----------------------- |
| Create Vite + React + TS project in `/frontend`                                  | Fast dev & prod builds  |
| Add Tailwind CSS, preload custom colour palette                                  | Blue‑teal trust palette |
| Base folder structure (`components/`, `pages/`, `services/`, etc.)               | Keeps code scalable     |
| Install core deps → **react‑router v6**, **axios**, **react‑hook‑form**, **zod** | Validation + routing    |
| Add `.env` with                                                                  |                         |
| `VITE_API_AUTH=http://localhost:8080`                                            |                         |
| `VITE_API_RESUME=http://localhost:8082`                                          |                         |
| `VITE_API_INTERVIEW=http://localhost:8081`                                       | Easy env‑switch         |
| Scaffold `App.tsx` with `<BrowserRouter>` & top‑level error boundary             |                         |

---

## Phase 2 – Re‑usable UI Kit (Checkpoint 2)

\* Header + NavBar with logo & dark/light switch
\* Button, Card, Input/TextArea, Toast, LoadingSpinner
\* FileUpload (drag‑drop + PDF/DOC/DOCX validation + progress)
\* Modal (generic)

**New** ➜ Toast util must be able to show *“Results also emailed to your‑email\@domain”* once backend response returns `email` field.

---

## Phase 3 – Lightweight Auth Flow (Checkpoint 3)

| Keep                                                              | Remove                                                    |
| ----------------------------------------------------------------- | --------------------------------------------------------- |
| Login & Register forms calling `/auth/login` and `/auth/register` | Password reset, profile edit, remember‑me, refresh tokens |
| Store JWT in **memory + localStorage** (fallback)                 | User settings pages                                       |
| `<ProtectedRoute>` wrapper for resume / job / interview pages     |                                                           |
| Logout button → clear token & redirect                            |                                                           |

---

## Phase 4 – Resume Analyzer Page (Checkpoint 4)

1. FileUpload → call **/resume/analyze**.
2. Display returned `score` (0‑100) with animated circle progress.
3. Render strengths / areas‑to‑improve bullets from JSON.
4. Show success toast: *“Detailed report also sent to {email}”*.
5. Reset form button.


---

## Phase 5 – Job Matcher Page (Checkpoint 5)

1. Resume file + Job description textarea inputs.
2. POST → **/resume/job‑match**.
3. Show compatibility score bar + bullet suggestions.
4. Toast with `email` confirmation.
5. Clear button.


---

## Phase 6 – Interview Prep Page (Checkpoint 6)

1. Inputs: Resume file + optional Job Role + Job Description (required by backend).
2. POST → **/resume/interview‑prep**.
3. Render list of Q\&A grouped by category.
4. Toast with `email` confirmation.


---

## Phase 7 – Polish & Accessibility (Checkpoint 7)

*Dark/light theming, responsive tweaks, keyboard nav, aria labels.*
*Smooth page transitions via **Framer Motion**.*

---

## Phase 8 – Testing & Perf (Checkpoint 8)

* Unit tests for utils/components.
* Integration test: login → protected page → upload resume → success toast.
* Lighthouse performance & a11y (≥ 90 scores).

---

## Agent Briefs (Lean)

### Marketing Agent

Highlight *“Instant AI feedback + email summary in seconds.”* Focus on busy grads & early professionals.

### Research Agent

Validate desire for instant email summaries & ability to reuse results in application tracking.

### Feature‑Planning Agent

Post‑MVP: enable history dashboard (requires new `/history` endpoint) & paid PDF reports.

---

## Technical Notes

*Frontend only calls the three ports; no need for 8083 listener.*
*Kubernetes / Docker compose remains unchanged for UI dev: run `docker‑compose up` then `npm run dev`.*
