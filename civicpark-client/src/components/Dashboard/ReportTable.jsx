import * as React from "react";
import { styled } from "@mui/material/styles";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import { useAccount } from "../../context/ContextProvider";
import { getReports } from "../../service/rtoOfficeService";
import { Button } from "../UI/Button";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  "&:last-child td, &:last-child th": {
    border: 0,
  },
}));

function createData(name, calories, fat, carbs, protein) {
  return { name, calories, fat, carbs, protein };
}

export default function ReportTable() {
  const { reportData, setReportData } = useAccount();

  React.useEffect(() => {
    const getData = async () => {
      const response = await getReports();
      console.log(response);
      setReportData(response.data);
    };
    getData();
  }, []);
  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 700 }} aria-label="customized table">
        <TableHead>
          <TableRow>
            <StyledTableCell>Report Id</StyledTableCell>
            <StyledTableCell align="right">City</StyledTableCell>
            <StyledTableCell align="right">Status</StyledTableCell>
            <StyledTableCell align="right">Verified By</StyledTableCell>
            <StyledTableCell align="right">Details</StyledTableCell>  
          </TableRow>
        </TableHead>
        <TableBody>
          {reportData?.map((row) => (
            <StyledTableRow key={row.reportId}>
              <StyledTableCell component="th" scope="row">
                {row.reportId}
              </StyledTableCell>
              <StyledTableCell align="right">{row.city}</StyledTableCell>
              <StyledTableCell align="right">
                {row.reportStatus}
              </StyledTableCell>
              <StyledTableCell align="right">{row.verifiedBy}</StyledTableCell>
              <StyledTableCell align="right" className="w-9">
                <a href={`/rto-dashboard/report/${row.reportId}`}>
                  <Button>Show</Button>
                </a>
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
