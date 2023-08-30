import {
  AlertDialog,
  AlertDialogBody,
  AlertDialogContent,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogOverlay,
  Button,
  IconButton,
  Tooltip,
  useDisclosure,
} from "@chakra-ui/react";
import React, { FunctionComponent } from "react";
import { AiFillDelete } from "react-icons/ai";

interface DeleteDialogProps {
  name: string;
  doAction: () => void;
}

const DeleteDialog: FunctionComponent<DeleteDialogProps> = ({
  name,
  doAction,
}) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const cancelRef = React.useRef(null);

  return (
    <>
      <Tooltip label={`Delete ${name}`} aria-label={`Delete ${name}`}>
        <IconButton
          aria-label={`Delete ${name}`}
          onClick={onOpen}
          variant="ghost"
        >
          <AiFillDelete />
        </IconButton>
      </Tooltip>
      <AlertDialog
        isOpen={isOpen}
        leastDestructiveRef={cancelRef}
        onClose={onClose}
      >
        <AlertDialogOverlay>
          <AlertDialogContent>
            <AlertDialogHeader fontSize="lg" fontWeight="bold">
              Delete {name}
            </AlertDialogHeader>

            <AlertDialogBody>
              Are you sure? You can't undo this action afterwards.
            </AlertDialogBody>

            <AlertDialogFooter>
              <Button ref={cancelRef} onClick={onClose}>
                Cancel
              </Button>
              <Button
                colorScheme="red"
                onClick={() => {
                  doAction();
                  onClose();
                }}
                ml={3}
              >
                Delete
              </Button>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialogOverlay>
      </AlertDialog>
    </>
  );
};

export default DeleteDialog;
