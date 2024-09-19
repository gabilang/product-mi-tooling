import React, { useState } from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button } from "@material-ui/core/";
import { useSelector } from 'react-redux';
import HTTPClient from '../utils/HTTPClient';
import { currentGroupSelector } from '../redux/Actions';

export default function CarbonLogsView() {
   const globalGroupId = useSelector(state => state.groupId);
   const [logContent, setLogContent] = useState('');
   const [open, setOpen] = useState(false);
   const selectedNodeList = useSelector(currentGroupSelector).selected;

    function fetchLogContent() {
        HTTPClient.getCarbonLogs(globalGroupId, selectedNodeList).then(response => {
            setLogContent(response.data);
            setOpen(true);
        });
    }

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <div>
            <h1 onClick={fetchLogContent}>Carbon Logs</h1>
            <Dialog open={open} onClose={handleClose} maxWidth="lg" fullWidth>
                <DialogTitle>Log Content: {'wso2carbon.log'}</DialogTitle>
                <DialogContent>
                    <pre>{JSON.stringify(logContent, null, 2)}</pre>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">Close</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
