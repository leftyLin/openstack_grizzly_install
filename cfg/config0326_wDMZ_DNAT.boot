firewall {
    all-ping enable
    broadcast-ping disable
    ipv6-receive-redirects disable
    ipv6-src-route disable
    ip-src-route disable
    log-martians enable
    name private_to_dmz {
        default-action drop
        description "filter traffic from private to dmz"
        rule 1 {
            action accept
            destination {
                port http,https,ftp,ssh,telnet
            }
            protocol tcp
        }
        rule 2 {
            action accept
            icmp {
                type-name any
            }
            protocol icmp
        }
    }
    name public_to_dmz {
        default-action drop
        description "filter traffic from public to dmz"
        rule 1 {
            action accept
            destination {
                port http,https
            }
            protocol tcp
        }
        rule 2 {
            action accept
            icmp {
                type-name any
            }
            protocol icmp
        }
    }
    name to_private {
        default-action drop
        description "filter traffic to private"
        rule 1 {
            action accept
            protocol all
            state {
                established enable
                related enable
            }
        }
    }
    name to_public {
        default-action drop
        description "allow all traffic to public"
        rule 1 {
            action accept
        }
    }
    receive-redirects disable
    send-redirects enable
    source-validation disable
    syn-cookies enable
}
interfaces {
    ethernet eth0 {
        address dhcp
        duplex auto
        hw-id 08:00:27:99:cf:83
        smp_affinity auto
        speed auto
    }
    ethernet eth1 {
        address dhcp
        duplex auto
        hw-id 08:00:27:cf:86:6e
        smp_affinity auto
        speed auto
    }
    ethernet eth2 {
        address dhcp
        duplex auto
        hw-id 08:00:27:51:37:20
        smp_affinity auto
        speed auto
    }
    ethernet eth3 {
        address dhcp
        duplex auto
        hw-id 08:00:27:18:26:75
        smp_affinity auto
        speed auto
    }
    loopback lo {
    }
}
nat {
    destination {
        rule 10 {
            destination {
                address 192.168.56.102
                port http,https
            }
            inbound-interface eth0
            protocol tcp
            translation {
                address 192.168.60.100
            }
        }
    }
    source {
        rule 10 {
            outbound-interface eth0
            source {
                address 192.168.58.0/24
            }
            translation {
                address masquerade
            }
        }
        rule 11 {
            outbound-interface eth0
            source {
                address 192.168.60.0/24
            }
            translation {
                address masquerade
            }
        }
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
zone-policy {
    zone dmz {
        default-action drop
        description DMZ
        from private {
            firewall {
                name private_to_dmz
            }
        }
        from public {
            firewall {
                name public_to_dmz
            }
        }
        interface eth1
    }
    zone private {
        default-action drop
        description PRIVATE
        from dmz {
            firewall {
                name to_private
            }
        }
        from public {
            firewall {
                name to_private
            }
        }
        interface eth2
    }
    zone public {
        default-action drop
        description PUBLIC
        from dmz {
            firewall {
                name to_public
            }
        }
        from private {
            firewall {
                name to_public
            }
        }
        interface eth0
    }
}


/* Warning: Do not remove the following line. */
/* === vyatta-config-version: "cluster@1:config-management@1:conntrack-sync@1:conntrack@1:dhcp-relay@1:dhcp-server@4:firewall@5:ipsec@4:nat@4:qos@1:quagga@2:system@6:vrrp@1:wanloadbalance@3:webgui@1:webproxy@1:zone-policy@1" === */
/* Release version: VC6.5R1 */
