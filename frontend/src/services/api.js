import axios from 'axios';

// API base URLs from environment variables
const API_URLS = {
  auth: import.meta.env.VITE_AUTH_API_URL || 'http://localhost:8080',
  resumeAnalyzer: import.meta.env.VITE_RESUME_ANALYZER_API_URL || 'http://localhost:8082',
  interviewPrep: import.meta.env.VITE_INTERVIEW_PREP_API_URL || 'http://localhost:8081',
  notification: import.meta.env.VITE_NOTIFICATION_API_URL || 'http://localhost:8083'
};

// Create axios instances for each service
const createApiInstance = (baseURL, serviceName) => {
  const instance = axios.create({
    baseURL,
    timeout: 30000, // 30 seconds timeout
    headers: {
      'Content-Type': 'application/json',
    },
  });

  // Request interceptor to add auth token
  instance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('authToken');
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  // Response interceptor for error handling
  instance.interceptors.response.use(
    (response) => response,
    (error) => {
      console.error(`${serviceName} API Error:`, error);
      
      if (error.response?.status === 401) {
        // Token expired or invalid
        localStorage.removeItem('authToken');
        window.location.href = '/login';
      }
      
      return Promise.reject(error);
    }
  );

  return instance;
};

// API instances
export const authAPI = createApiInstance(API_URLS.auth, 'Auth');
export const resumeAnalyzerAPI = createApiInstance(API_URLS.resumeAnalyzer, 'Resume Analyzer');
export const interviewPrepAPI = createApiInstance(API_URLS.interviewPrep, 'Interview Prep');
export const notificationAPI = createApiInstance(API_URLS.notification, 'Notification');

// Auth service functions
export const authService = {
  login: (credentials) => authAPI.post('/auth/authenticate', credentials),
  register: (userData) => authAPI.post('/auth/register', userData),
  logout: () => {
    localStorage.removeItem('authToken');
    // Additional logout logic can be added here
  }
};

// Resume analyzer service functions
export const resumeAnalyzerService = {
  analyzeResume: (formData) => resumeAnalyzerAPI.post('/analysis/analyze', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  }),
  matchJob: (formData) => resumeAnalyzerAPI.post('/job-match/match', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  })
};

// Interview prep service functions
export const interviewPrepService = {
  prepareInterview: (data) => interviewPrepAPI.post('/interview/prepare', data)
};

// Utility function to handle API errors
export const handleApiError = (error) => {
  if (error.response) {
    // Server responded with error status
    return {
      message: error.response.data?.message || 'An error occurred',
      status: error.response.status,
      data: error.response.data
    };
  } else if (error.request) {
    // Request was made but no response received
    return {
      message: 'Network error. Please check your connection.',
      status: null,
      data: null
    };
  } else {
    // Something else happened
    return {
      message: 'An unexpected error occurred',
      status: null,
      data: null
    };
  }
};