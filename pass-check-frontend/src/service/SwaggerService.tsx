export async function getSwaggerDocumentation(): Promise<string> {
    const response = await fetch("/api/doc");

    if(!response.ok) {
        throw new Error("Network was not ok");
    }

    return await response.json();
}