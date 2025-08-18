import React from "react";
import { Button } from "../UI/Button";

export const Card = ({ name, button, description }) => {
  return (
    <div className="flex flex-col h-48 w-80 bg-slate-200 rounded-md p-4 justify-between">
      <div className="text-md">{name}</div>
      <div className="text-sm text-gray-600 pt-4">{description}</div>
      <div className="mt-auto">
        <a href="/rto-user/report">
          <Button>{button}</Button>
        </a>
      </div>
      <div></div>
    </div>
  );
};
