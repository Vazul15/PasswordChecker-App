import { useEffect } from "react";

const DocumentationPage = () => {
  useEffect(() => {
    window.location.href = "/api/doc";
  }, []);

  return <h1>Swagger documentation loading...</h1>;
};

export default DocumentationPage;
