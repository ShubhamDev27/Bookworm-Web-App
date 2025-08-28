import React from 'react';
import './CartItemCard.css';

// Component for a single item in the cart
const CartItemCard = ({ item, onQuantityChange, onRemove }) => {
  // 1. Create a placeholder image URL, similar to BookCard.
  //    The text is encoded to ensure the URL is valid.
  const placeholderImage = `https://placehold.co/100x150/9c27b0/white?text=${encodeURIComponent(item.title)}`;

  // 2. Use the item's image if it exists, otherwise use the placeholder.
  const imageUrl = item.image || placeholderImage;

  return (
    <div className="cart-item card p-3 mb-3">
      <div className="row g-0 align-items-center">
        <div className="col-auto me-3">
          <img
            src={imageUrl}
            className="cart-item-img rounded"
            alt={item.title}
            // 3. Add an onError handler to catch broken image links and show the placeholder instead.
            onError={(e) => {
              e.target.onerror = null; // Prevents infinite loops if the placeholder also fails
              e.target.src = placeholderImage;
            }}
          />
        </div>
        <div className="col">
          <h6 className="fw-bold mb-0">{item.title}</h6>
          <p className="text-muted mb-1">{item.author}</p>
          <p className="text-muted mb-2">{item.type}</p>
          <div className="d-flex align-items-center">
            <div className="quantity-selector me-4">
              <button
                className="btn btn-outline-secondary btn-sm"
                onClick={() => onQuantityChange(item.id, item.quantity - 1)}
                disabled={item.quantity <= 1}
              >
                -
              </button>
              <span className="mx-2">{item.quantity}</span>
              <button
                className="btn btn-outline-secondary btn-sm"
                onClick={() => onQuantityChange(item.id, item.quantity + 1)}
              >
                +
              </button>
            </div>
            <button className="btn btn-link text-muted p-0" onClick={() => onRemove(item.id)}>
              <i className="bi bi-trash me-1"></i> Remove
            </button>
          </div>
        </div>
        <div className="col-auto fw-bold text-end">
          ₹{(item.price * item.quantity).toFixed(2)}
        </div>
      </div>
    </div>
  );
};

export default CartItemCard;