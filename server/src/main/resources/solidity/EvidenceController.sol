pragma solidity ^0.4.25;

import "./Authentication.sol";
import "./EvidenceRepository.sol";
import "./Utils.sol";

contract EvidenceFactory {
    struct App {
        bool valid;
        EvidenceRepository evidenceRepo;
    }
    mapping(bytes32=>App) public apps;
    event EvidenceSave(string appId, bytes32 hash);

    function createApp(string appId) {
        bytes32 _appId = Utils.stringToBytes32(appId);
        require(!apps[_appId].valid, "应用已经注册");
        apps[_appId].valid = true;
        apps[_appId].evidenceRepo = new EvidenceRepository();
    }

    function getEvidenceRepo(string appId) private view returns(EvidenceRepository) {
        bytes32 _appId = Utils.stringToBytes32(appId);
        require(apps[_appId].valid, "应用尚未注册");
        return apps[_appId].evidenceRepo;
    }

    function createUser(string appId, address addr) {
        EvidenceRepository repo = getEvidenceRepo(appId);
        repo.allow(addr);
    }

    function save(string appId, bytes32 hash, string name, string dataUrl, uint8 dataType, string ext) {
        EvidenceRepository repo = getEvidenceRepo(appId);
        repo.setData(hash, name, dataUrl, dataType, ext);
        emit EvidenceSave(appId, hash);
    }

    function query(string appId, bytes32 hash) public view returns(bytes32, string, string, uint8, string) {
        EvidenceRepository repo = getEvidenceRepo(appId);
        return repo.getData(hash);
    }
}