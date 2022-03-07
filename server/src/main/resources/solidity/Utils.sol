pragma solidity ^0.4.25;

library Utils {

    function stringToBytes32(string memory source) internal pure returns (bytes32 result) {
        assembly {
            result := mload(add(source, 32))
        }
    }
}