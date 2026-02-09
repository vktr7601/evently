import {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import axios from 'axios';

// Defined outside to prevent re-creation on every render
const EVENT_CATEGORIES = [
  "Live Music", "Comedy", "Theater", "Festivals", "Workshops", "Art Gallery"
];

const Register = () => {
  // Initial state including the previously missing selectedCategories array

  const [categories, setCategories] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:9000/events")
      .then(res => {
        const data = Array.isArray(res.data) ? res.data : res.data?.content || [];
        setCategories(data);
      })
      .catch(err => {
        console.error(err);
      });
  }, []);
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    age: '',
    email: '',
    password: '',
    selectedCategories: []
  });

  const handleChange = (e) => {
    const {name, value} = e.target;
    setFormData({...formData, [name]: value});
  };

  // Specialized handler for the category toggle buttons
  const handleCategoryToggle = (category) => {
    setFormData(prev => {
      const isSelected = prev.selectedCategories.includes(category);
      return {
        ...prev,
        selectedCategories: isSelected
          ? prev.selectedCategories.filter(c => c !== category)
          : [...prev.selectedCategories, category]
      };
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Registration Submission:", formData);

    // Integrate your axios.post here
  };

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-12 col-md-8 col-lg-6">

          <div className="card shadow-sm border-0 p-4">
            <div className="card-body">
              <h2 className="card-title fw-bold mb-2 text-center">Create Account</h2>
              <p className="text-muted text-center mb-4 small">Join our community of art and music lovers</p>

              <form onSubmit={handleSubmit}>
                <div className="row">
                  <div className="col-md-6 mb-3">
                    <label className="form-label small fw-bold text-uppercase"
                           style={{fontSize: '0.75rem', letterSpacing: '0.5px'}}>First Name</label>
                    <input
                      name="firstName"
                      type="text"
                      className="form-control bg-light border-0"
                      placeholder="John"
                      value={formData.firstName}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div className="col-md-6 mb-3">
                    <label className="form-label small fw-bold text-uppercase"
                           style={{fontSize: '0.75rem', letterSpacing: '0.5px'}}>Last Name</label>
                    <input
                      name="lastName"
                      type="text"
                      className="form-control bg-light border-0"
                      placeholder="Doe"
                      value={formData.lastName}
                      onChange={handleChange}
                      required
                    />
                  </div>
                </div>

                <div className="mb-3">
                  <label className="form-label small fw-bold text-uppercase"
                         style={{fontSize: '0.75rem', letterSpacing: '0.5px'}}>Age</label>
                  <input
                    name="age"
                    type="number"
                    className="form-control bg-light border-0"
                    placeholder="25"
                    value={formData.age}
                    onChange={handleChange}
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label small fw-bold text-uppercase"
                         style={{fontSize: '0.75rem', letterSpacing: '0.5px'}}>Email Address</label>
                  <input
                    name="email"
                    type="email"
                    className="form-control bg-light border-0"
                    placeholder="name@example.com"
                    value={formData.email}
                    onChange={handleChange}
                    required
                  />
                </div>

                {/* Password */}
                <div className="mb-4">
                  <label className="form-label small fw-bold text-uppercase"
                         style={{fontSize: '0.75rem', letterSpacing: '0.5px'}}>Password</label>
                  <input
                    name="password"
                    type="password"
                    className="form-control bg-light border-0"
                    placeholder="••••••••"
                    value={formData.password}
                    onChange={handleChange}
                    required
                  />
                </div>

                {/* Category Selection Section */}
                <div className="mb-4 p-3 rounded" style={{backgroundColor: '#f8f9fa'}}>
                  <label className="form-label small fw-bold d-block mb-3">
                    <i className="bi bi-bell-fill me-2"></i>Notify me about:
                  </label>
                  <div className="d-flex flex-wrap gap-2">
                    {EVENT_CATEGORIES.map((cat) => {
                      const isActive = formData.selectedCategories.includes(cat);
                      return (
                        <button
                          key={cat}
                          type="button"
                          onClick={() => handleCategoryToggle(cat)}
                          className={`btn btn-sm rounded-pill px-3 transition-all ${
                            isActive ? 'btn-dark' : 'btn-outline-secondary text-muted'
                          }`}
                          style={{
                            borderStyle: isActive ? 'solid' : 'dashed',
                            fontSize: '0.8rem'
                          }}
                        >
                          {cat} {isActive && <i className="bi bi-check-lg ms-1"></i>}
                        </button>
                      );
                    })}
                  </div>
                </div>

                {/* Submit Button */}
                <div className="d-grid pt-2">
                  <button
                    type="submit"
                    className="btn btn-dark rounded-pill py-2 fw-bold shadow-sm"
                  >
                    Register Now
                  </button>
                </div>

                <div className="text-center mt-4">
                  <span className="text-muted small">Already have an account? </span>
                  <Link to="/login" className="text-dark small fw-bold text-decoration-none">Log In</Link>
                </div>
              </form>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
};

export default Register;