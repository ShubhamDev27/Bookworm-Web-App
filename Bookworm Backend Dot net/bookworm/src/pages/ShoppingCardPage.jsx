import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import './ShoppingCardPage.css';
import CartItemCard from '../components/Card/CartItemCard';
import OrderSummary from '../components/Card/OrderSummery';
import YouMayAlsoLike from '../components/Card/YouMayAlsoLike';
import Footer from '../components/Footer';

const ShoppingCartPage = () => {
    const [cartItems, setCartItems] = useState([]);
    const [promoDiscount, setPromoDiscount] = useState(0);
    const [loading, setLoading] = useState(true);
    const [isCheckingOut, setIsCheckingOut] = useState(false);
    const [showSuccessPopup, setShowSuccessPopup] = useState(false);
    const { customerId: urlCustomerId } = useParams();

    // Parse user data from localStorage to get customerId
    const userData = localStorage.getItem('user');
    const customerId = userData ? JSON.parse(userData).id : urlCustomerId || null;

    useEffect(() => {
        const fetchCartData = async () => {
            if (!customerId) {
                setCartItems([]);
                setLoading(false);
                return;
            }
            const token = localStorage.getItem('token');
            if (!token) {
                setCartItems([]);
                setLoading(false);
                return;
            }
            try {
                const response = await fetch(`https://localhost:7261/api/v1/carts/${customerId}`, {
                    headers: { 'Authorization': `Bearer ${token}` }
                });
                if (!response.ok) {
                    setCartItems([]); // Treat failed response as empty cart
                } else {
                    const data = await response.json();
                    if (!data || !Array.isArray(data.items)) {
                        setCartItems([]); // Invalid data format treated as empty cart
                    } else {
                        const formattedItems = data.items.map(item => ({
                            id: item.productId,
                            title: item.productName,
                            author: item.author,
                            price: item.itemCost,
                            quantity: item.quantity,
                            image: item.imageSource,
                            type: item.rented ? 'Rented Book' : 'Book',
                        }));
                        setCartItems(formattedItems);
                    }
                }
            } catch (err) {
                setCartItems([]); // Network error or other exceptions treated as empty cart
            } finally {
                setLoading(false);
            }
        };
        fetchCartData();
    }, [customerId]);

    const handleRemoveItem = async (productId) => {
        const token = localStorage.getItem('token');
        if (!token) {
            return;
        }
        try {
            const response = await fetch(`https://localhost:7261/api/v1/carts/${customerId}/items/${productId}`, {
                method: 'DELETE',
                headers: { 'Authorization': `Bearer ${token}` }
            });
            if (!response.ok) {
                throw new Error("Failed to remove the item from your cart.");
            }
            setCartItems(currentItems =>
                currentItems.filter(item => item.id !== productId)
            );
        } catch (err) {
            // Optionally, handle specific errors (e.g., show a toast notification)
        }
    };

    const handleQuantityChange = (productId, newQuantity) => {
        console.log(`TODO: Implement API call to update product ${productId} to quantity ${newQuantity}`);
    };

    const handleCheckout = async () => {
        setIsCheckingOut(true);
        const token = localStorage.getItem('token');
        if (!token) {
            setIsCheckingOut(false);
            return;
        }
        try {
            const response = await fetch(`https://localhost:7261/api/orders/create-from-cart/${customerId}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (!response.ok) {
                throw new Error('Failed to create order from cart.');
            }
            const data = await response.json();
            setCartItems([]); // Clear cart after successful checkout
            setShowSuccessPopup(true); // Show success popup
            setTimeout(() => setShowSuccessPopup(false), 3000); // Auto-hide after 3 seconds
        } catch (err) {
            // Optionally, handle specific errors (e.g., show a toast notification)
        } finally {
            setIsCheckingOut(false);
        }
    };

    const handlePromoApply = (promoCode) => {
        console.log(`Applying promo code: ${promoCode}`);
        setPromoDiscount(10); // Example: Apply a fixed discount
    };

    const subtotal = cartItems.reduce((acc, item) => acc + item.price * item.quantity, 0);
    const estimatedTax = 0;

    if (loading) {
        return <div className="container my-5 text-center"><h2>Loading Your Cart...</h2></div>;
    }

    return (
        <div className="shopping-cart-page container">
            <div className="row">
                <div className="col-lg-8">
                    <h1 className="fw-bold">Shopping Cart</h1>
                    <p className="text-muted mb-4">Review and checkout your items</p>
                    {cartItems.length > 0 ? (
                        cartItems.map((item) => (
                            <CartItemCard
                                key={item.id}
                                item={item}
                                onRemove={handleRemoveItem}
                                onQuantityChange={handleQuantityChange}
                            />
                        ))
                    ) : (
                        <div className="card p-5 text-center text-muted">
                            Your cart is empty.
                        </div>
                    )}
                    <button className="btn btn-outline-secondary mt-3">Continue Shopping</button>
                </div>
                <div className="col-lg-4">
                    <OrderSummary
                        subtotal={subtotal}
                        discount={promoDiscount}
                        estimatedTax={estimatedTax}
                        onPromoApply={handlePromoApply}
                        onCheckout={handleCheckout}
                        isCheckingOut={isCheckingOut}
                    />
                </div>
            </div>
            <YouMayAlsoLike />
            {showSuccessPopup && (
                <div className="success-popup" style={{ zIndex: 1000, position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', background: 'rgba(0, 0, 0, 0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                    <div className="success-popup-content" style={{ background: '#fff', padding: '20px 30px', borderRadius: '10px', boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)', textAlign: 'center', maxWidth: '400px', width: '90%' }}>
                        <h4 className="text-success">Success!</h4>
                        <p>Your order has been placed successfully.</p>
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

export default ShoppingCartPage;