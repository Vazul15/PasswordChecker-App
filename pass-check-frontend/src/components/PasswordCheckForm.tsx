import { getPathIfPasswordFound } from "@/service/FindPassService";
import { useState } from "react";
import type { paths } from "./types";
import { Button } from "./ui/button";

export const PasswordCheckForm = () => {
  const [pathName, setPathName] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [resultPaths, setResultPaths] = useState<paths | null>(null);
  const [errorMessage, setErrorMessage] = useState<string>("");

  const prefixFileName: string = "/app/testPurpose/";

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    try {
      const fullPath: string = prefixFileName + pathName;
      const paths = await getPathIfPasswordFound(fullPath, password);
      setResultPaths(paths);
      setErrorMessage("");
    } catch (error) {
      if (error instanceof Error) {
        setErrorMessage(error.message);
      } else {
        setErrorMessage("An unknown error occurred.");
      }
    }
  };

  return (
    <div className="flex justify-center items-center h-screen">
      <div className="bg-white shadow-lg rounded-lg p-6 w-full max-w-md">
        <h2 className="text-2xl font-semibold text-gray-800 text-center mb-4">
          Password Checker
        </h2>
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <label className="text-gray-700 font-medium">
            Enter the file path:
            <div className="flex items-center gap-2">
              <span className="text-gray-500">{prefixFileName}</span>
              <input
                type="text"
                value={pathName}
                onChange={(e) => setPathName(e.target.value)}
                className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                placeholder="filename"
              />
            </div>
          </label>

          <label className="text-gray-700 font-medium">
            Enter the password to check:
            <input
              type="text"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full mt-1 p-2 border rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              required
            />
          </label>

          <Button
            type="submit"
            className="w-full bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600 transition"
          >
            Check Password
          </Button>
        </form>
        {errorMessage && (
          <div className="mt-4 p-4 bg-red-200 text-red-800 rounded-lg opacity-90 text-lg">
            <p>{errorMessage}</p>
          </div>
        )}

        {resultPaths && resultPaths.paths.length > 0 && (
          <div className="mt-4 p-4 bg-gray-200 rounded-lg">
            <h3 className="text-lg font-semibold text-gray-800">
              Files where the password was found:
            </h3>
            <ul className="list-disc list-inside text-gray-700">
              {resultPaths.paths.map((path, index) => (
                <li key={index}>{path}</li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </div>
  );
};
