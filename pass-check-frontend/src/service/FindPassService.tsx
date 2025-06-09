import type { paths } from "../components/types";

export async function getPathIfPasswordFound(
  path: string,
  password: string
): Promise<paths> {
  const response = await fetch(
    `/api/findpass/?path=${path}&password=${password}`
  );

  if (!response.ok) {
    const errorMessage = await response.text();
    throw new Error(`Error ${response.status}: ${errorMessage}`);
  }

  const filesWherePasswordFound = await response.json();
  return filesWherePasswordFound;
}
