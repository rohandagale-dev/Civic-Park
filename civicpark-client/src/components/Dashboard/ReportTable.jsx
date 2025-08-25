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
import { Input } from "../UI/Input";
import { getReportByVehicleNumber } from "../../service/reportService";

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

export default function ReportTable() {
  const { reportData, setReportData } = useAccount();
  const [search, setSearch] = React.useState();

  const handleSearch = (e) => {
    setSearch(e.target.value);
  };

  const handleSearchSubmit = async () => {
    if (search != null) {
      const response = await getReportByVehicleNumber(search);
      setReportData(response.data);
    }
  };

  React.useEffect(() => {
    const getData = async () => {
      const response = await getReports();
      console.log(response);
      setReportData(response.data);
    };
    getData();
  }, []);

  return (
    <div>
      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-8 gap-4 mb-6 items-center w-full grid-rows-2">
        <div className="col-span-2 col-start-6 self-end items-end">
          <Input
            id="search"
            name="search"
            type="text"
            placeholder="Search by vehicle number"
            value={search}
            onChange={handleSearch}
          />
        </div>
        <div className="pt-1">
          <Button className="w-full" onClick={handleSearchSubmit}>
            Search
          </Button>
        </div>
      </div>
      <TableContainer component={Paper} className="max-h-[500px] overflow-y-auto">
        <Table
          stickyHeader
          sx={{ minWidth: 700 }}
          aria-label="customized table"
          className="scroll-auto"
        >
          <TableHead>
            <TableRow>
              <StyledTableCell>Report Id</StyledTableCell>
              <StyledTableCell align="center">Vehicle Number</StyledTableCell>
              <StyledTableCell align="center">Status</StyledTableCell>
              <StyledTableCell align="center">Verified By</StyledTableCell>
              <StyledTableCell align="center">Evidence</StyledTableCell>
              <StyledTableCell align="center">Details</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {reportData &&
              reportData?.map((row) => (
                <StyledTableRow key={row.reportId}>
                  <StyledTableCell component="th" scope="row" align="left">
                    {row.reportId}
                  </StyledTableCell>
                  <StyledTableCell align="center">{row?.vehicleNumber}</StyledTableCell>
                  <StyledTableCell align="center">{row.reportStatus}</StyledTableCell>
                  <StyledTableCell align="center">{row.verifiedBy}</StyledTableCell>
                  <StyledTableCell align="center">{row?.evidences?.length}</StyledTableCell>
                  <StyledTableCell align="center" className="w-9">
                    <a href={`/rto-dashboard/report/${row.reportId}`}>
                      <Button onClick={handleSearchSubmit}>Show</Button>
                    </a>
                  </StyledTableCell>
                </StyledTableRow>
              ))}
          </TableBody>
        </Table>
        {reportData && reportData.length == 0 ? (
          <>
            <div className="w-full h-16 text-center flex flex-row justify-center items-center">
              <p>No Result Found</p>
            </div>
          </>
        ) : (
          <></>
        )}
      </TableContainer>
    </div>
  );
}
