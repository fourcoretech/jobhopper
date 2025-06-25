import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ErrorBoundary from './components/ErrorBoundary';

// Placeholder components that we'll create in future phases
const HomePage = () => (
  <div className="min-h-screen bg-gray-50 flex items-center justify-center">
    <div className="text-center">
      <h1 className="text-4xl font-bold text-gray-900 mb-4">
        AI-Powered Resume Analyzer
      </h1>
      <p className="text-lg text-gray-600 mb-8">
        Transform your career with AI-driven insights
      </p>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 max-w-4xl mx-auto px-4">
        {/* Resume Analyzer Card */}
        <div className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow">
          <div className="h-12 w-12 bg-primary-100 rounded-lg flex items-center justify-center mb-4 mx-auto">
            <svg className="h-6 w-6 text-primary-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
            </svg>
          </div>
          <h3 className="text-lg font-semibold text-gray-900 mb-2">Resume Analyzer</h3>
          <p className="text-gray-600 text-sm">
            Get instant feedback and scoring on your resume with AI-powered analysis
          </p>
        </div>

        {/* Job Matcher Card */}
        <div className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow">
          <div className="h-12 w-12 bg-success-100 rounded-lg flex items-center justify-center mb-4 mx-auto">
            <svg className="h-6 w-6 text-success-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
            </svg>
          </div>
          <h3 className="text-lg font-semibold text-gray-900 mb-2">Job Matcher</h3>
          <p className="text-gray-600 text-sm">
            Compare your resume against job descriptions for perfect alignment
          </p>
        </div>

        {/* Interview Prep Card */}
        <div className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow">
          <div className="h-12 w-12 bg-warning-100 rounded-lg flex items-center justify-center mb-4 mx-auto">
            <svg className="h-6 w-6 text-warning-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
          </div>
          <h3 className="text-lg font-semibold text-gray-900 mb-2">Interview Prep</h3>
          <p className="text-gray-600 text-sm">
            Practice with AI-generated questions tailored to your experience
          </p>
        </div>
      </div>
    </div>
  </div>
);

const LoginPage = () => (
  <div className="min-h-screen bg-gray-50 flex items-center justify-center">
    <div className="text-center">
      <h2 className="text-2xl font-bold text-gray-900">Login Page</h2>
      <p className="text-gray-600">Coming soon in Phase 3</p>
    </div>
  </div>
);

const RegisterPage = () => (
  <div className="min-h-screen bg-gray-50 flex items-center justify-center">
    <div className="text-center">
      <h2 className="text-2xl font-bold text-gray-900">Register Page</h2>
      <p className="text-gray-600">Coming soon in Phase 3</p>
    </div>
  </div>
);

const ResumeAnalyzerPage = () => (
  <div className="min-h-screen bg-gray-50 flex items-center justify-center">
    <div className="text-center">
      <h2 className="text-2xl font-bold text-gray-900">Resume Analyzer</h2>
      <p className="text-gray-600">Coming soon in Phase 4</p>
    </div>
  </div>
);

const JobMatcherPage = () => (
  <div className="min-h-screen bg-gray-50 flex items-center justify-center">
    <div className="text-center">
      <h2 className="text-2xl font-bold text-gray-900">Job Matcher</h2>
      <p className="text-gray-600">Coming soon in Phase 5</p>
    </div>
  </div>
);

const InterviewPrepPage = () => (
  <div className="min-h-screen bg-gray-50 flex items-center justify-center">
    <div className="text-center">
      <h2 className="text-2xl font-bold text-gray-900">Interview Prep</h2>
      <p className="text-gray-600">Coming soon in Phase 6</p>
    </div>
  </div>
);

function App() {
  return (
    <ErrorBoundary>
      <Router>
        <div className="App">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/resume-analyzer" element={<ResumeAnalyzerPage />} />
            <Route path="/job-matcher" element={<JobMatcherPage />} />
            <Route path="/interview-prep" element={<InterviewPrepPage />} />
          </Routes>
        </div>
      </Router>
    </ErrorBoundary>
  );
}

export default App;