import { Outlet } from 'react-router-dom';
import Navbar from '../components/Navbar';
import Footer from '../components/Footer';

// 3 componentes, navbar, outlet y footer
export default function MainLayout() {
  return (
    <>
      <Navbar />
      <main className="container-fluid py-4 flex-grow-1">
        <Outlet />
      </main>
      <Footer />
    </>
  );
}
