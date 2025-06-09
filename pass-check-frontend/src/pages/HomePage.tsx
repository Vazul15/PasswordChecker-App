import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";

const HomePage = () => {
  const navigate = useNavigate();

  const handleHistoryClick = () => {
    navigate("/history");
  };

  const handlePasswordCheckClick = () => {
    navigate("/findpass");
  };

  const handleNavigationSwaggerUI = () => {
    navigate("/doc");
  };
  return (
    <div className="flex items-center justify-center h-screen w-screen space-x-4">
      <Button onClick={handleHistoryClick} className="text-xl px-12 py-8">
        View History
      </Button>
      <Button onClick={handlePasswordCheckClick}>Password check</Button>
      <Button onClick={handleNavigationSwaggerUI}>Documentation</Button>
    </div>
  );
};

export default HomePage;
