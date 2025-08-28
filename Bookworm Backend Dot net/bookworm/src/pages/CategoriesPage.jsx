import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Footer from '../components/Footer';
import BookCard from '../components/BookCard';
import BookDetailModal from '../components/BookDetailModal';
import AddToCartModal from '../components/AddToCartModal';

const axiosInstance = axios.create({
  baseURL: 'https://localhost:7261/api/v1',
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

const CategoriesPage = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [searchType, setSearchType] = useState('name');
  const [allBooks, setAllBooks] = useState([]);
  const [results, setResults] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');
  const [selectedBook, setSelectedBook] = useState(null);
  const [bookDetailLoading, setBookDetailLoading] = useState(false);

  const [isCartModalOpen, setIsCartModalOpen] = useState(false);
  const [cartModalMode, setCartModalMode] = useState('buy'); // 'buy' or 'rent'
  const [selectedBookForCart, setSelectedBookForCart] = useState(null);
  const [showSuccessPopup, setShowSuccessPopup] = useState(false); // New state for success popup

  useEffect(() => {
    const fetchAllBooks = async () => {
      setIsLoading(true);
      try {
        const response = await axiosInstance.get('/products');
        setAllBooks(response.data);
        setResults(response.data);
      } catch (err) {
        console.error("Error fetching all books:", err);
        setError('Failed to load the book collection.');
      } finally {
        setIsLoading(false);
      }
    };
    fetchAllBooks();
  }, []);

  useEffect(() => {
    if (!searchTerm.trim()) {
      setResults(allBooks);
      return;
    }
    const delayDebounceFn = setTimeout(async () => {
      setIsLoading(true);
      try {
        const params = { [searchType]: searchTerm };
        const response = await axiosInstance.get(`/products/search/by-${searchType}`, { params });
        setResults(response.data);
      } catch (err) {
        console.error(`Error searching by ${searchType}:`, err);
        setError('Failed to fetch search results.');
        setResults([]);
      } finally {
        setIsLoading(false);
      }
    }, 500);
    return () => clearTimeout(delayDebounceFn);
  }, [searchTerm, searchType, allBooks]);

  const openCartModal = (book, mode) => {
    const userStr = localStorage.getItem("user");
    if (!userStr || userStr === "undefined") {
      alert("You must be logged in to add items to the cart.");
      return;
    }

    const user = JSON.parse(userStr);
    if (!user.id) {
      alert("Could not find your user ID. Please try logging in again.");
      return;
    }
    
    setSelectedBookForCart(book);
    setCartModalMode(mode);
    setIsCartModalOpen(true);
  };

  const confirmAddToCart = async ({ quantity, rentNumberOfDays }) => {
    const user = JSON.parse(localStorage.getItem("user"));
    const customerId = user.id;

    const payload = {
      productId: selectedBookForCart.id,
      quantity: quantity,
      isRented: cartModalMode === 'rent',
      rentNumberOfDays: rentNumberOfDays,
    };

    try {
      await axiosInstance.post(`/carts/${customerId}/items`, payload);
      setShowSuccessPopup(true); // Show success popup
      setTimeout(() => setShowSuccessPopup(false), 3000); // Auto-hide after 3 seconds
    } catch (err) {
      console.error('Failed to add item to cart:', err);
      if (err.response && (err.response.status === 401 || err.response.status === 403)) {
        alert('Your session has expired. Please log in again.');
      } else {
        alert('There was an error adding the item to your cart. Please try again.');
      }
    } finally {
      setIsCartModalOpen(false);
    }
  };

  const handleCardClick = async (bookId) => {
    try {
      setBookDetailLoading(true);
      const response = await axiosInstance.get(`/products/${bookId}`);
      setSelectedBook(response.data);
    } catch (err) {
      alert('Failed to load book details');
      console.error(err);
    } finally {
      setBookDetailLoading(false);
    }
  };

  return (
    <div className="categories-page container ">
      <div className="text-center text-md-start mb-4">
        <h1 className="fw-bold">Search Collection</h1>
        <p className="text-muted">Discover books and audiobooks by name or author.</p>
      </div>

      <div className="input-group mb-4">
        <select
          className="form-select flex-grow-0"
          style={{ width: '120px' }}
          value={searchType}
          onChange={(e) => setSearchType(e.target.value)}
        >
          <option value="name">By Name</option>
          <option value="author">By Author</option>
        </select>
        <input
          type="text"
          className="form-control"
          placeholder={`Search for a book by ${searchType}...`}
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>

      {error && <div className="alert alert-danger">{error}</div>}
      {isLoading && <div className="text-center"><p>Loading...</p></div>}

      <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5 g-4">
        {!isLoading && results.map((item) => (
          <div key={item.id} className="col">
            <BookCard
              book={item}
              onAddToCart={openCartModal}
              onCardClick={handleCardClick}
            />
          </div>
        ))}
      </div>

      {!isLoading && !results.length && (
        <div className="text-center p-5">
          <p className="text-muted fs-4">{searchTerm.trim() ? 'No results found.' : 'No books available.'}</p>
        </div>
      )}

      {isCartModalOpen && selectedBookForCart && (
        <AddToCartModal
          book={selectedBookForCart}
          mode={cartModalMode}
          onClose={() => setIsCartModalOpen(false)}
          onConfirm={confirmAddToCart}
        />
      )}

      {selectedBook && (
        <BookDetailModal book={selectedBook} onClose={() => setSelectedBook(null)} />
      )}

      {showSuccessPopup && (
        <div className="success-popup" style={{ zIndex: 1000, position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', background: 'rgba(0, 0, 0, 0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
          <div className="success-popup-content" style={{ background: '#fff', padding: '20px 30px', borderRadius: '10px', boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)', textAlign: 'center', maxWidth: '400px', width: '90%' }}>
            <h4 className="text-success">Success!</h4>
            <p>{`"${selectedBookForCart.name}" has been added to your cart.`}</p>
            <button
              className="btn btn-success mt-2"
              onClick={() => setShowSuccessPopup(false)}
              style={{ padding: '8px 20px', fontWeight: 500 }}
            >
              OK
            </button>
          </div>
        </div>
      )}

      <Footer />
    </div>
  );
};

export default CategoriesPage;