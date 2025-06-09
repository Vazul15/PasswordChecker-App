import type { history } from "../components/types";

export async function getAllHistory(): Promise<history[]> {
    const response = await fetch("/api/history/")

    if(!response.ok) {
        throw new Error("Network was not ok");
    }

    const historyData = await response.json();
    return historyData;
}
