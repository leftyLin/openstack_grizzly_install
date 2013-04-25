interfaces {
    ethernet eth0 {
        address dhcp
        duplex auto
        smp_affinity auto
        speed auto
    }
    ethernet eth1 {
        address dhcp
        duplex auto
        smp_affinity auto
        speed auto
    }
    ethernet eth2 {
        address dhcp
        duplex auto
        smp_affinity auto
        speed auto
    }
    ethernet eth3 {
        address dhcp
        duplex auto
        smp_affinity auto
        speed auto
    }
    loopback lo {
    }
}
service {
    ssh {
        port 22
    }
}
system {
    config-management {
        commit-revisions 20
    }
    console {
        device ttyS0 {
            speed 9600
        }
    }
    host-name vyatta
    login {
        user vyatta {
            authentication {
                encrypted-password $1$4XHPj9eT$G3ww9B/pYDLSXC8YVvazP0
            }
            level admin
        }
    }
    ntp {
        server 0.vyatta.pool.ntp.org {
        }
        server 1.vyatta.pool.ntp.org {
        }
        server 2.vyatta.pool.ntp.org {
        }
    }
    package {
        auto-sync 1
        repository community {
            components main
            distribution stable
            password ""
            url http://packages.vyatta.com/vyatta
            username ""
        }
    }
    syslog {
        global {
            facility all {
                level notice
            }
            facility protocols {
                level debug
            }
        }
    }
    time-zone GMT
}


/* Warning: Do not remove the following line. */
/* === vyatta-config-version: "cluster@1:config-management@1:conntrack-sync@1:conntrack@1:dhcp-relay@1:dhcp-server@4:firewall@5:ipsec@4:nat@4:qos@1:quagga@2:system@6:vrrp@1:wanloadbalance@3:webgui@1:webproxy@1:zone-policy@1" === */
/* Release version: VC6.5R1 */
