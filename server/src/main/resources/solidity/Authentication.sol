pragma solidity ^0.4.25;

// 用户A->合约1->合约2
// msg.sender = 合约1地址
// tx.origin = 用户A

contract Authentication {
    address public _owner;
    mapping(address=>bool) private _acl;

    constructor() public {
      _owner = tx.origin;
    } 

    modifier onlyOwner() {
      require(tx.origin == _owner, "用户不是管理员");
      _;
    }

    modifier auth() {
      require(tx.origin == _owner || _acl[tx.origin] == true, "用户不允许操作");
      _;
    }

    function allow(address addr) public onlyOwner {
      _acl[addr] = true;
    }

    function deny(address addr) public onlyOwner {
      _acl[addr] = false;
    }
}