import { Outlet } from "react-router-dom";
import Header from "./Header";
import Footer from "./Footer";

export default function MainLayout() {
  return (
    <>
      <Header />
      <main className="container-fluid py-4 flex-grow-1">
        <Outlet />
      </main>
      <Footer />
    </>
  );
}
