import React, { useState, useEffect } from 'react';
import ShelfCard from '../components/ShelfCard';
import Footer from '../components/Footer';
import './MyShelfPage.css';

const MyShelfPage = () => {
  const [activeTab, setActiveTab] = useState('Rented');
  const [rentedItems, setRentedItems] = useState([]);
  const [purchasedItems, setPurchasedItems] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Get customer ID from user object in localStorage and token separately
  const userStr = localStorage.getItem('user') || '{}';
  const user = JSON.parse(userStr);
  const customerId = user.id || null;
  const token = localStorage.getItem('token') || null;

  // Generic fetch function to handle both item types
  const fetchItems = async (type) => {
    const endpoint = type === 'Rented' ? 'library' : 'shelf';
    if (!customerId || !token) {
      setError('Please log in to view your shelf.');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const response = await fetch(`https://localhost:7261/user-collection/${customerId}/${endpoint}`, {
        method: 'GET',
        headers: {
          // CORRECTED: Using standard 'Bearer' token authorization
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        credentials: 'include',
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const data = await response.json();
      console.log(`API response for ${type} items:`, data); // For debugging

      let items = [];
      // CORRECTED: Robustly check for the array of items
      if (data && Array.isArray(data.content)) {
        items = data.content; // Handles paginated response { "content": [...] }
      } else if (Array.isArray(data)) {
        items = data; // Handles raw array response [...]
      } else {
        console.warn(`Received unexpected data format for ${type} items:`, data);
      }
      
      if (type === 'Rented') {
        setRentedItems(items);
      } else {
        setPurchasedItems(items);
      }

    } catch (err) {
      setError(`Failed to fetch ${type.toLowerCase()} items: ${err.message}`);
    } finally {
      setLoading(false);
    }
  };

  // Fetch data when component mounts or activeTab changes
  useEffect(() => {
    fetchItems(activeTab);
  }, [activeTab, customerId, token]); // Dependencies are correct

  const getGridContent = (items) => {
    return items.map((item, index) => (
      <div key={item.productId || `item-${index}`} className="col">
        <ShelfCard item={{ ...item, type: activeTab }} />
      </div>
    ));
  };

  // Handle case where user is not logged in
  if (!customerId || !token) {
    return (
      <div className="my-shelf-page container py-5">
        <h1 className="fw-bold">My Shelf</h1>
        <p className="text-danger">Please log in to view your shelf.</p>
        <Footer />
      </div>
    );
  }

  const currentItems = activeTab === 'Rented' ? rentedItems : purchasedItems;

  return (
    <div className="my-shelf-page container py-5">
      <div className="text-center text-md-start mb-4">
        <h1 className="fw-bold">My Shelf</h1>
        <p className="text-muted">Access your Purchased and Rented content</p>
      </div>

      <div className="mb-4">
        <button
          className={`btn ${activeTab === 'Rented' ? 'btn-primary' : 'btn-outline-primary'} me-2`}
          onClick={() => setActiveTab('Rented')}
        >
          Rented
        </button>
        <button
          className={`btn ${activeTab === 'Purchased' ? 'btn-primary' : 'btn-outline-primary'}`}
          onClick={() => setActiveTab('Purchased')}
        >
          Purchased
        </button>
      </div>

      {loading && <p>Loading...</p>}
      {error && <p className="text-danger">{error}</p>}

      <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4 mb-5">
        {/* ADDED: Handle empty state */}
        {!loading && !error && currentItems.length > 0 ? (
          getGridContent(currentItems)
        ) : (
          !loading && !error && <p className="col-12">You have no {activeTab.toLowerCase()} items.</p>
        )}
      </div>

      <Footer />
    </div>
  );
};

export default MyShelfPage;