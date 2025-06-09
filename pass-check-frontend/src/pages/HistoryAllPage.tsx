import { useEffect, useState } from "react";
import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import type { history } from "../components/types";
import { getAllHistory } from "@/service/HistoryService";

export const HistoryAll = () => {
  const [history, setHistory] = useState<history[]>([]);

  useEffect(() => {
    const getHistoryData = async () => {
      const historyData = await getAllHistory();

      setHistory(historyData);
    };

    getHistoryData();
  }, []);

  return (
    <div className="pt-5">
      <Table>
        <TableCaption>Request history log</TableCaption>
        <TableHeader>
          <TableRow>
            <TableHead>IP Address</TableHead>
            <TableHead>Port</TableHead>
            <TableHead>Method</TableHead>
            <TableHead>URI</TableHead>
            <TableHead>Query</TableHead>
            <TableHead>Local Address</TableHead>
            <TableHead>User Agent</TableHead>
            <TableHead>Timestamp</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {history.map((historyData, index) => (
            <TableRow key={index}>
              <TableCell>{historyData.ipAddress}</TableCell>
              <TableCell>{historyData.port}</TableCell>
              <TableCell>{historyData.requestMethod}</TableCell>
              <TableCell>{historyData.requestUri}</TableCell>
              <TableCell>{historyData.queryParams}</TableCell>
              <TableCell>{historyData.localAddress}</TableCell>
              <TableCell>{historyData.userAgent}</TableCell>
              <TableCell>
                {new Date(historyData.timestamp).toLocaleString()}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};
