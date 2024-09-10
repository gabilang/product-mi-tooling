import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { TableCell, Dialog, DialogTitle, DialogContent, DialogActions, Button } from "@material-ui/core/";
import { useSelector } from 'react-redux';
import HTTPClient from '../utils/HTTPClient';

export default function LogsNodeCell(props) {
    const globalGroupId = useSelector(state => state.groupId);
    const { nodeId, fileName } = props;
    const [logContent, setLogContent] = useState('');
    const [open, setOpen] = useState(false);

    function fetchLogContent() {
        const resourcePath = '/groups/'.concat(globalGroupId).concat('/nodes/').concat(nodeId).concat('/logs/').concat(fileName);
        HTTPClient.get(resourcePath).then(response => {
            setLogContent(response.data);
            setOpen(true);
        });
    }

    const handleClose = () => {
        setOpen(false);
    };

    const classes = useStyles();
    return (
        <div>
            <TableCell className={classes.tableCell} onClick={fetchLogContent}>
                {nodeId}
            </TableCell>
            <Dialog open={open} onClose={handleClose} maxWidth="lg" fullWidth>
                <DialogTitle>Log Content: {fileName}</DialogTitle>
                <DialogContent>
                    <pre>{logContent}</pre>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">Close</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

const useStyles = makeStyles(() => ({
    tableCell: {
        padding: '1px',
        borderBottom: 'none',
        display: 'flex',
        cursor: 'pointer',
        color: '#3f51b5',
    }
}));
