import { Route, Routes } from "react-router-dom";
import DocumentationPage from "./pages/DocumentationPage";
import HomePage from "./pages/HomePage";
import { HistoryAll } from "./pages/HistoryAllPage";
import { PasswordChecker } from "./pages/PasswordChecker";

function App() {
  return (
    <div className="bg-background h-screen w-screen">
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/doc" element={<DocumentationPage />} />
        <Route path="/history" element={<HistoryAll />} />
        <Route path="/findpass" element={<PasswordChecker />} />
      </Routes>
    </div>
  );
}

export default App;
